import { useState } from "react";

export default function Topbar() {
  const [{ today, greeting }] = useState(() => {
    const date = new Date();
    const hour = date.getHours();
    return {
      today: date.toLocaleDateString("en-IN", { weekday: "short", day: "numeric", month: "short", year: "numeric" }),
      greeting: hour < 12 ? "Good morning" : hour < 17 ? "Good afternoon" : "Good evening",
    };
  });

  return <header className="topbar"><a className="brand" href="/">Market Intelligence</a><div className="topbar-right"><div className="date-block"><b>{greeting}</b><span>{today}</span></div></div></header>;
}
