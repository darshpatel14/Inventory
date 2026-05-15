import React, { useEffect, useState } from "react";
import axios from "axios";
import "./css/inventory.css";
import { useNavigate } from "react-router-dom";
import closeimg from "./icon/close.png";

function Inventory() {
  const [inventory, setInventory] = useState([]);

  const [error, setError] = useState("");

  const navigate = useNavigate();

  const [fromDate, setFromDate] = useState("");
  const [toDate, setToDate] = useState("");

  const [showPopup, setShowPopup] = useState(false);

  const [showEmailPopup, setShowEmailPopup] = useState(false);

  const [emailData, setEmailData] = useState({
    email: "",
    type: "csv",
  });

  // FETCH INVENTORY
  useEffect(() => {
    fetchInventory();
  }, []);

  const fetchInventory = async () => {
    try {
      setInventory({
        fromDate: "",
        toDate: "",
        emailData: "",
      });

      const response = await axios.get("http://localhost:8080/inventory/data");

      setInventory(response.data);
    } catch (err) {
      setError("Failed to load inventory data");
    }
  };

  // DOWNLOAD
  const handleDownload = (type) => {
    if (!fromDate || !toDate) {
      alert("Please select dates");

      return;
    }

    window.open(
      `http://localhost:8080/inventory/export/${type}` +
        `?fromDate=${fromDate}&toDate=${toDate}`,
      "_blank",
    );

    setShowPopup(false);
  };

  // SEND EMAIL
  const sendEmail = async () => {
    if (!emailData.email) {
      alert("Please enter email");

      return;
    }

    try {
      const response = await axios.post(
        "http://localhost:8080/inventory/email-report",
        {
          email: emailData.email,
          type: emailData.type,
          fromDate: fromDate,
          toDate: toDate,
        },
      );

      alert(response.data);

      setShowEmailPopup(false);

      setShowPopup(false);
    } catch (error) {
      console.log(error);

      alert("Failed to send email");
    }
  };

  if (error) {
    return <h2>{error}</h2>;
  }

  return (
    <div className="inventory-container">
      <div className="inventory-header">
        <h2>Inventory Details</h2>

        <button className="download-btn" onClick={() => setShowPopup(true)}>
          Download
        </button>
      </div>

      <table className="inventory-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Product Name</th>
            <th>Stock In</th>
            <th>Stock Out</th>
            <th>Current Stock</th>
            <th>Last Updated</th>
          </tr>
        </thead>

        <tbody>
          {inventory.length > 0 ? (
            inventory.map((item) => (
              <tr key={item.id}>
                <td>{item.id}</td>

                <td>{item.productName}</td>

                <td>{item.stockIn}</td>

                <td>{item.stockOut}</td>

                <td>{item.currentStock}</td>

                <td>
                  {item.lastUpdated
                    ? new Date(item.lastUpdated).toLocaleString()
                    : "N/A"}
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="6">No Inventory Data Found</td>
            </tr>
          )}
        </tbody>
      </table>

      {/* DOWNLOAD POPUP */}

      {showPopup && (
        <div className="download-popup">
          <div className="popup-box">
            <div className="popup-div-1">
              <img
                className="popup-close"
                src={closeimg}
                alt="Close"
                onClick={() => setShowPopup(false)}
              />
            </div>

            <div className="popup-div-2">
              <label className="popup-label">From:</label>
              <label className="popup-label">To:</label>
            </div>

            <div className="popup-div-3">
              <input
                className="input-date"
                type="date"
                value={fromDate}
                onChange={(e) => setFromDate(e.target.value)}
              />

              <input
                className="input-date"
                type="date"
                value={toDate}
                onChange={(e) => setToDate(e.target.value)}
              />
            </div>

            <div className="popup-div-4">
            
              <button onClick={() => handleDownload("csv")}>CSV</button>

              <button onClick={() => handleDownload("pdf")}>PDF</button>

              <button onClick={() => handleDownload("json")}>JSON</button>

              <button>Img</button>

              <button onClick={() => navigate("/inventory-chart")}>
                Bar Graph
              </button>

              <button
                onClick={() => {
                  if (!fromDate || !toDate) {
                    alert("Please select dates");
                    return;
                  }

                  setShowEmailPopup(true);
                }}
              >
                Email
              </button>
            </div>
          </div>
        </div>
      )}

      {/* EMAIL POPUP */}

      {showEmailPopup && (
        <div className="download-popup">
          <div className="popup-box">

            <div className="popup-div-1">
              <img
                className="popup-close"
                src={closeimg}
                alt="Close"
                onClick={() => setShowEmailPopup(false)}
              />
            </div>

            <input
              className="input-email"
              type="email"
              placeholder="Enter Email"
              value={emailData.email}
              onChange={(e) =>
                setEmailData({
                  ...emailData,
                  email: e.target.value,
                })
              }
            />

            <select
              className="input-type"
              value={emailData.type}
              onChange={(e) =>
                setEmailData({
                  ...emailData,
                  type: e.target.value,
                })
              }
            >
              <option value="csv">CSV</option>

              <option value="pdf">PDF</option>
            </select>

            <button className="email-submit-btn"  onClick={sendEmail}>Send Email</button>
          </div>
        </div>
      )}
    </div>
  );
}

export default Inventory;
 