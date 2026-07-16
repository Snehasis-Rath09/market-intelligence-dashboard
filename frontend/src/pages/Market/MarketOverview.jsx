import { useEffect, useMemo, useRef, useState } from "react";
import {
    FiArrowUpRight,
    FiGlobe,
    FiLoader,
    FiMapPin,
    FiRefreshCw,
    FiSearch,
    FiShoppingCart,
    FiTrendingUp
} from "react-icons/fi";
import { Area, AreaChart, Bar, BarChart, CartesianGrid, ResponsiveContainer, Tooltip, XAxis, YAxis } from "recharts";
import client from "../../api/axiosClient";

const currency = (value, compact = false) => new Intl.NumberFormat("en-IN", { style: "currency", currency: "INR", notation: compact ? "compact" : "standard", maximumFractionDigits: compact ? 1 : 0 }).format(Number(value || 0));
const number = (value) => new Intl.NumberFormat("en-IN", { notation: "compact", maximumFractionDigits: 1 }).format(Number(value || 0));
const mapPositions = { US: [24, 43], Canada: [22, 31], LATAM: [32, 71], Europe: [50, 35], EMEA: [55, 48], Africa: [51, 65], APAC: [76, 49], "Pacific Asia": [78, 53] };

function ChartTooltip({ active, payload, label }) { if (!active || !payload?.length) return null; return <div className="chart-tooltip"><span>{label}</span><strong>{currency(payload[0].value)}</strong></div>; }

function DemandMap({ data, query }) {
  const markets = Object.values(data.reduce((grouped, item) => {
    const current = grouped[item.market] || { ...item, totalSales: 0, regions: [] };
    current.totalSales += Number(item.totalSales || 0);
    current.regions.push(item.region);
    grouped[item.market] = current;
    return grouped;
  }, {})).sort((left, right) => right.totalSales - left.totalSales);
  const strongest = Math.max(...markets.map((item) => Number(item.totalSales)), 1);
  return <div className="map-wrap">
    <svg className="world-map" viewBox="0 0 1000 510" role="img" aria-label="World map with sales demand markers">
      <path d="M72 118l49-51 88-13 57 31 29 56-31 45-8 64-54 14-35-44-57 1-43-42zM283 263l55 11 36 56-10 110-42 51-25-74-33-90zM438 112l49-44 75 12 37 37-19 27-57-3-22 41-44-9zM497 192l73 13 49 75-30 54-25 108-49-17-29-94 21-70-37-29zM631 107l76-45 114 16 58 46-24 39-79 11-61 42-79-28-39-42zM729 244l77 25 59 67-26 52-56-1-52-60zM834 378l48 14 28 42-55 25-35-29z" />
    </svg>
    {markets.map((item, index) => { const position = mapPositions[item.market] || [52 + (index % 3) * 8, 42 + (index % 4) * 8]; const intensity = Number(item.totalSales) / strongest; return <div className="map-marker" key={item.market} style={{ left: `${position[0]}%`, top: `${position[1]}%`, "--size": `${18 + intensity * 30}px`, "--glow": intensity }}><span /><div className="map-label"><b>{item.market}</b><small>{currency(item.totalSales, true)}</small></div></div>; })}
    <div className="map-scale"><span>Lower demand</span><i /><span>Higher demand</span></div>
    <div className="map-caption"><FiMapPin /> {query ? `Demand for “${query}”` : "Worldwide sales density"}</div>
  </div>;
}

export default function MarketOverview() {
  const [categoryPerformance, setCategoryPerformance] = useState([]), [regionalDemand, setRegionalDemand] = useState([]), [monthlyTrend, setMonthlyTrend] = useState([]), [geography, setGeography] = useState([]);
  const [search, setSearch] = useState(""), [searchResults, setSearchResults] = useState([]), [summary, setSummary] = useState(null), [loading, setLoading] = useState(true), [refreshing, setRefreshing] = useState(false), [error, setError] = useState("");
  const searchTimer = useRef();
  const metrics = useMemo(() => {

    const revenue = categoryPerformance.reduce(
        (sum, item) => sum + Number(item.totalSales || 0),
        0
    );

    const profit = categoryPerformance.reduce(
        (sum, item) => sum + Number(item.totalProfit || 0),
        0
    );

    const orders = geography.length;

    const markets = new Set(
        geography.map(item => item.market)
    ).size;

    return {

        revenue,

        profit,

        orders,

        markets,

        margin: revenue
            ? (profit / revenue) * 100
            : 0

    };

}, [categoryPerformance, geography]);
  useEffect(() => { loadData(); return () => clearTimeout(searchTimer.current); }, []);
  async function loadData() { setError(""); setRefreshing(true); try { const [category, region, trend, map] = await Promise.all([client.get("/market/category-performance"), client.get("/market/regional-demand"), client.get("/market/monthly-trend"), client.post("/market/geography-demand", {})]); setCategoryPerformance(category.data.data || []); setRegionalDemand(region.data.data || []); setMonthlyTrend(trend.data.data || []); setGeography(map.data.data || []); } catch (err) { console.error("Market analytics request failed", err); setError("We couldn’t load market analytics. Check that the backend is running, then try again."); } finally { setLoading(false); setRefreshing(false); } }
  async function runSearch(keyword) { try { const [searchResponse, mapResponse] = await Promise.all([client.post("/market/search", { keyword }), client.post("/market/geography-demand", { keyword })]); setSummary(searchResponse.data.data.summary); setSearchResults(searchResponse.data.data.results || []); setGeography(mapResponse.data.data || []); } catch (err) { console.error("Market search failed", err); setSummary(null); setSearchResults([]); } }
  function handleSearch(event) { const keyword = event.target.value; setSearch(keyword); clearTimeout(searchTimer.current); if (keyword.trim().length < 2) { setSummary(null); setSearchResults([]); client.post("/market/geography-demand", {}).then((response) => setGeography(response.data.data || [])); return; } searchTimer.current = setTimeout(() => runSearch(keyword.trim()), 320); }
  if (loading) return <div className="dashboard-loading"><FiLoader /> <span>Preparing your market view…</span></div>;
  return <div className="market-dashboard">
    <section className="dashboard-hero"><div className="hero-copy"><div className="eyebrow">Market intelligence</div><h1>Clarity for every <em>commercial move.</em></h1><p>A refined, visual read on performance, demand, and the places your customers are buying.</p></div><div className="hero-stat"><span>Portfolio value</span><strong>{currency(metrics.revenue, true)}</strong><small><FiTrendingUp /> Sales across every market</small></div></section>
    <section className="dashboard-toolbar"><div className="search-wrap"><FiSearch /><input value={search} onChange={handleSearch} placeholder="Search a product, country, customer or category…" aria-label="Search market data" />{search && <button className="clear-search" onClick={() => handleSearch({ target: { value: "" } })}>Clear</button>}</div><button className="refresh-button" onClick={loadData} disabled={refreshing}><FiRefreshCw className={refreshing ? "spin" : ""} /> {refreshing ? "Refreshing" : "Refresh"}</button></section>
    {error && <div className="dashboard-error">{error}<button onClick={loadData}>Try again</button></div>}
    <section className="metric-grid">

    <article className="metric-card">

        <span className="metric-icon purple">
            <FiTrendingUp />
        </span>

        <div>
            <p>Global Revenue</p>
            <h2>{currency(metrics.revenue, true)}</h2>
            <small>Across all categories</small>
        </div>

    </article>

    <article className="metric-card">

        <span className="metric-icon gold">
            <FiArrowUpRight />
        </span>

        <div>
            <p>Net Profit</p>
            <h2>{currency(metrics.profit, true)}</h2>
            <small>{metrics.margin.toFixed(1)}% profit margin</small>
        </div>

    </article>

    <article className="metric-card">

        <span className="metric-icon blue">
            <FiGlobe />
        </span>

        <div>
            <p>Markets Covered</p>
            <h2>{metrics.markets}</h2>
            <small>Worldwide market presence</small>
        </div>

    </article>

    <article className="metric-card">

        <span className="metric-icon green">
            <FiShoppingCart />
        </span>

        <div>
            <p>Regions Analysed</p>
            <h2>{metrics.orders}</h2>
            <small>Business regions tracked</small>
        </div>

    </article>

</section>
    {summary && <section className="search-summary"><div><span>Search intelligence</span><h2>{summary.totalRecords.toLocaleString()} matching records</h2></div><div><span>Search sales</span><strong>{currency(summary.totalSales, true)}</strong></div><div><span>Search profit</span><strong>{currency(summary.totalProfit, true)}</strong></div></section>}
    <section className="map-panel"><div className="panel-heading"><div><span>Demand geography</span><h2>{search ? `Where “${search}” sells` : "Sales concentration around the world"}</h2></div><p>The brightest points represent the highest sales density.</p></div><DemandMap data={geography} query={search.trim()} /></section>
    <section className="chart-grid compact-bars"><article className="panel"><div className="panel-heading"><div><span>Portfolio composition</span><h2>Category sales</h2></div><p>Revenue by category</p></div><div className="category-chart"><ResponsiveContainer width="100%" height="100%"><BarChart data={categoryPerformance} barCategoryGap="34%" margin={{ top: 12, right: 8, left: -20, bottom: 0 }}><CartesianGrid vertical={false} stroke="#ebe9e4" /><XAxis dataKey="category" tickLine={false} axisLine={false} tick={{ fill: "#655f76", fontSize: 11 }} /><YAxis tickFormatter={(value) => currency(value, true)} tickLine={false} axisLine={false} tick={{ fill: "#938e89", fontSize: 10 }} /><Tooltip content={<ChartTooltip />} /><Bar dataKey="totalSales" fill="#7255d8" radius={[5, 5, 0, 0]} /></BarChart></ResponsiveContainer></div></article><article className="panel"><div className="panel-heading"><div><span>Regional demand</span><h2>Region-wise sales</h2></div><p>Revenue by region</p></div><div className="category-chart"><ResponsiveContainer width="100%" height="100%"><BarChart data={regionalDemand} barCategoryGap="18%" margin={{ top: 12, right: 8, left: -20, bottom: 0 }}><CartesianGrid vertical={false} stroke="#ebe9e4" /><XAxis dataKey="region" tickLine={false} axisLine={false} tick={{ fill: "#655f76", fontSize: 10 }} /><YAxis tickFormatter={(value) => currency(value, true)} tickLine={false} axisLine={false} tick={{ fill: "#938e89", fontSize: 10 }} /><Tooltip content={<ChartTooltip />} /><Bar dataKey="totalSales" fill="#d39b4a" radius={[5, 5, 0, 0]} /></BarChart></ResponsiveContainer></div></article></section>
    <section className="panel results-panel"><div className="panel-heading"><div><span>{search ? "Search results" : "Explore the dataset"}</span><h2>{search ? `Results for “${search}”` : "Search the market"}</h2></div>{searchResults.length > 0 && <div className="result-count">{searchResults.length} displayed</div>}</div>{searchResults.length ? <div className="table-scroll"><table><thead><tr><th>Product</th><th>Category</th><th>Market</th><th>Sales</th><th>Profit</th><th>Qty</th></tr></thead><tbody>{searchResults.map((item, index) => <tr key={`${item.productName}-${index}`}><td><b>{item.productName}</b><small>{item.city}, {item.country}</small></td><td><span className="category-pill">{item.category}</span></td><td>{item.region}</td><td>{currency(item.sales)}</td><td className={Number(item.profit) < 0 ? "negative" : "positive"}>{currency(item.profit)}</td><td>{item.quantity}</td></tr>)}</tbody></table></div> : <div className="empty-results"><FiSearch /><h3>Start with a focused question</h3><p>Search a product, city, country, or category to reveal its sales footprint.</p></div>}</section>
  </div>;
}