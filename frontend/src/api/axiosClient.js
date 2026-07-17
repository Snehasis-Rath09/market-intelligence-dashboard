import axios from "axios";

const client = axios.create({
    baseURL: "https://market-intelligence-dashboard-qp74.onrender.com/api"
});

export default client;
