import React, { useEffect, useRef, useState } from "react";
import axios from "axios";

import {
    Bar
} from "react-chartjs-2";

import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
} from "chart.js";

import html2canvas from "html2canvas";

import "./css/inventorychart.css";

ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
);

function InventoryChart() {

    const [inventory, setInventory] = useState([]);

    const chartRef = useRef();



    // FETCH DATA
    useEffect(() => {

        fetchInventory();

    }, []);



    const fetchInventory = async () => {

        try {

            const response = await axios.get(
                "http://localhost:8080/inventory/data"
            );

            setInventory(response.data);

        }
        catch (error) {

            console.log(error);
        }
    };



    // CHART DATA
    const data = {

        labels: inventory.map(
            item => item.productName
        ),

        datasets: [
            {
                label: "Current Stock",

                data: inventory.map(
                    item => item.currentStock
                ),

                backgroundColor: [
                    "#42a5f5",
                    "#66bb6a",
                    "#ffa726",
                    "#ef5350",
                    "#ab47bc"
                ],
            }
        ]
    };



    // DOWNLOAD IMAGE
    const downloadChart = async () => {

        const canvas = await html2canvas(
            chartRef.current
        );

        const image = canvas.toDataURL("image/png");

        const link = document.createElement("a");

        link.href = image;

        link.download = "inventory-chart.png";

        link.click();
    };



    return (

        <div className="chart-page">

            <h2>Inventory Analytics</h2>

            <div
                className="chart-container"
                ref={chartRef}
            >

                <Bar data={data} />

            </div>

            <button
                className="download-chart-btn"
                onClick={downloadChart}
            >
                Download Chart
            </button>

        </div>


        
    );
}

export default InventoryChart;