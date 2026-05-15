import AddCategory from "./components/AddCategory";
import AddCustomer from "./components/AddCustomer";
import AddProduct from "./components/AddProduct";
import AddPurchase from "./components/AddPurchase";
import AddSupplier from "./components/AddSupplier";
import AddSales from "./components/AddSales";
import AddUser from "./components/AddUser";
import Inventory from "./components/Inventory";
import InventoryChart from "./components/InventoryChart";
import Dashboard from "./components/Dashboard";

import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

function App() {
  return (
    <Router>

      <Routes>
        <Route path="/" element={<Dashboard />} />
        <Route path="/add-category" element={<AddCategory />} />
        <Route path="/add-customer" element={<AddCustomer />} />
        <Route path="/add-supplier" element={<AddSupplier />} />
        <Route path="/add-product" element={ <AddProduct/>} />
        <Route path="/add-purchase" element={ <AddPurchase/>} />
        <Route path="/add-sales" element={ <AddSales/>} />
        <Route path="/add-user" element={ <AddUser/>} />
        <Route path="/view-Inventory" element={ <Inventory/>} />
        <Route path="/inventory-chart" element={<InventoryChart />} />


      </Routes>

    </Router>
  );
}

export default App;