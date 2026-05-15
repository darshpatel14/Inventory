import React, { useState } from "react";
import axios from "axios";
import "./css/user.css"; 

function Login() {
  const [user, setUser] = useState({
    username: "",
    password: "",
    email: "",
    phone: "",
    role: "",
  });

  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    setUser({
      ...user,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await axios.post("http://localhost:8080/inventory/users", user);
      alert("User saved successfully!");

      setUser({
        username: "",
        password: "",
        email: "",
        phone: "",
        role: "",
      });

      setErrors({});
    } catch (error) {
      if (error.response && error.response.data) {
        const data = error.response.data;
        if (typeof data === "object" && !data.message) {
          setErrors(data);
        } else if (data.message) {
          alert(data.message);
        }
      } else {
        alert("Something went wrong");
      }
    }
  };

  return (
    <div className="login-body">
      <div className="login-container">
        <h1>Inventory Management System</h1>
        <h2>User Login</h2>

        <form onSubmit={handleSubmit}>


          <div className="input-group">
            <label>Username</label>
            <input
              type="text"
              name="username"
              value={user.username}
              onChange={handleChange}
            />
            {errors.username && (
            <p className="error">{errors.username}</p>
          )}
          </div>

          <div className="input-group">
            <label>Password</label>
            <input
              type="password"
              name="password"
              value={user.password}
              onChange={handleChange}
            />
            {errors.password && (
            <p className="error">{errors.password}</p>
          )}
          </div>

          <div className="input-group">
            <label>Email</label>
            <input
              type="email"
              name="email"
              value={user.email}
              onChange={handleChange}
            />
            {errors.email && (
            <p className="error">{errors.email}</p>
          )}
          </div>

          <div className="input-group">
            <label>Phone</label>
            <input
              type="text"
              name="phone"
              value={user.phone}
              onChange={handleChange}
            />
            {errors.phone && (
            <p className="error">{errors.phone}</p>
          )}
          </div>

          <div className="input-group">
            <label>Role</label>
            <select name="role" value={user.role} onChange={handleChange}>
              <option value="ADMIN">Admin</option>
              <option value="MANAGER">Manager</option>
            </select>
          </div>

      <div className="user-button-row">
          <button type="submit">Save Purchase</button>
          <button type="button">Back to Dashboard</button>
        </div>         
      
      </form>
      </div>
    </div>
  );
}

export default Login;
