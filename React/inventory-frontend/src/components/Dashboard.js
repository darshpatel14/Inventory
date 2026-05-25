import React, { useEffect, useRef, useState } from "react";
import axios from "axios";
import "./css/dashboard.css";
import { useNavigate } from "react-router-dom";

import { Bar } from "react-chartjs-2";

import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";

import html2canvas from "html2canvas";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
);

function Dashboard() {
  const [data, setData] = useState({
    totalProducts: 0,
    totalCategories: 0,
    totalSuppliers: 0,
    totalPurchase: 0,
    totalSales: 0,
  });

  const [inventory, setInventory] = useState([]);

  const chartRef = useRef();

  const navigate = useNavigate();

  useEffect(() => {
    fetchDashboard();
    fetchInventory();
  }, []);

  // FETCH INVENTORY
  const fetchInventory = async () => {
    try {
      const response = await axios.get("http://localhost:8080/inventory/data");

      // corrected response
      setInventory(response.data);
    } catch (error) {
      console.log(error);
    }
  };

  // FETCH DASHBOARD DATA
  const fetchDashboard = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/inventory/dashboard",
      );

      console.log("API Response:", response.data);

      setData(response.data);
    } catch (error) {
      console.error(error);
      alert("Error loading dashboard");
    }
  };

  // CHART DATA
  const chartData = {
    labels: inventory.map((item) => item.productName),

    datasets: [
      {
        label: "Current Stock",

        // corrected data property
        data: inventory.map((item) => item.currentStock),

        backgroundColor: [
          "#42a5f5",
          "#66bb6a",
          "#ffa726",
          "#ef5350",
          "#ab47bc",
        ],
      },
    ],
  };

  // CHART OPTIONS
  const options = {
    responsive: true,

    plugins: {
      legend: {
        position: "top",
      },

      title: {
        display: true,
        text: "Inventory Stock Analytics",
      },
    },
  };

  // DOWNLOAD CHART
  const downloadChart = async () => {
    const canvas = await html2canvas(chartRef.current);

    const image = canvas.toDataURL("image/png");

    const link = document.createElement("a");

    link.href = image;
    link.download = "inventory-chart.png";

    link.click();
  };

  return (
    <div className="main-container">
      {/* Sidebar */}
      <div className="navigation-bar">
        <h1 className="heading">Inventory System</h1>

        <button className="btn" onClick={() => navigate("/add-category")}>
          Add Category
        </button>

        <button className="btn" onClick={() => navigate("/add-customer")}>
          Add Customer
        </button>

        <button className="btn" onClick={() => navigate("/add-supplier")}>
          Add Supplier
        </button>

        <button className="btn" onClick={() => navigate("/add-product")}>
          Add Product
        </button>

        <button className="btn" onClick={() => navigate("/add-purchase")}>
          Add Purchase
        </button>

        <button className="btn" onClick={() => navigate("/add-sales")}>
          Add Sales
        </button>

        <button className="btn" onClick={() => navigate("/add-user")}>
          Add User
        </button>

        <button className="btn" onClick={() => navigate("/view-Inventory")}>
          View Inventory
        </button>

        
      </div>

      {/* Main Content */}
      <div className="main-content">
        <h2 className="dashboard-heading">Welcome to Dashboard</h2>

        <div className="dashboard-header-content">
          <div className="dashboard-header-box">
            <label className="header-box-label">Total Products</label>
            <label className="header-box-data">{data.totalProducts}</label>
          </div>

          <div className="dashboard-header-box">
            <label className="header-box-label">Total Category</label>
            <label className="header-box-data">{data.totalCategories}</label>
          </div>

          <div className="dashboard-header-box">
            <label className="header-box-label">Total Supplier</label>
            <label className="header-box-data">{data.totalSuppliers}</label>
          </div>

          <div className="dashboard-header-box">
            <label className="header-box-label">Total Purchase</label>
            <label className="header-box-data">{data.totalPurchase}</label>
          </div>

          <div className="dashboard-header-box">
            <label className="header-box-label">Total Sales</label>
            <label className="header-box-data">{data.totalSales}</label>
          </div>
        </div>

        {/* <h2>Inventory Analytics</h2> */}

        {/* <div className="chart-container" ref={chartRef}>
          <Bar data={chartData} options={options} />
        </div> */}

        {/* <button
                    className="download-chart-btn"
                    onClick={downloadChart}
                >
                    Download Chart
                </button> */}
      </div>
    </div>
  );
}

export default Dashboard;
