import React, { useState, useEffect } from "react";
import apiClient from "./apiClient";
import "../styles/UpdateLocationAdmin.css"; // Import the CSS file
import Header from "./Header";
import Footer from "./Footer";

const UpdateLocationAdmin = () => {
  const [locations, setLocations] = useState([]); // State to store all locations
  const [selectedLocation, setSelectedLocation] = useState(""); // Selected location ID
  const [newAddress, setNewAddress] = useState(""); // New address input
  const [message, setMessage] = useState(""); // Success/error messages

  // Fetch all centers when component loads
  useEffect(() => {
    const fetchLocations = async () => {
      try {
        const response = await apiClient.get("/api/serviceontario/all-centers");
        setLocations(response.data); // Store locations in state
      } catch (error) {
        console.error("Error fetching locations:", error);
        setMessage("Failed to fetch locations. Please try again later.");
      }
    };

    fetchLocations();
  }, []);

 // Update location handler
 const handleUpdateLocation = async () => {
    if (!selectedLocation || !newAddress) {
      setMessage("Please select a location and provide a new address.");
      return;
    }

    try {
      const response = await apiClient.put("/api/serviceontario/admin/update-location", {
        locationId: selectedLocation,
        address: newAddress,
      });

      setMessage(response.data); // display success message
      setNewAddress(""); // reset new address input
    } catch (error) {
      console.error("Error updating location:", error);

      if (error.response?.status === 403) {
        // Unauthorized error
        setMessage("You are not authorized to modify this page. Please use admin credentials.");
      } else {
        setMessage(
          error.response?.data || "Failed to update location. Please try again."
        );
      }
    }
  };


  return (
    <div className="main-content">
      <Header />
      <h1>Update Location</h1>
     
      {message && <p className="message" style={{ color: message.includes("successfully") ? "green" : "red" }}>{message}</p>}

      <div className="location-select">
        <label htmlFor="locationSelect">Select Location:</label>
        <select
          id="locationSelect"
          value={selectedLocation}
          onChange={(e) => setSelectedLocation(e.target.value)}
        >
          <option value="">-- Select a Location --</option>
          {locations.map((location) => (
            <option key={location.id} value={location.id}>
              {location.name} - {location.address}
            </option>
          ))}
        </select>
      </div>

      <div className="location-select">
        <label htmlFor="newAddress">New Address:</label>
        <input
          id="newAddress"
          type="text"
          value={newAddress}
          onChange={(e) => setNewAddress(e.target.value)}
          placeholder="Enter new address"
        />
      </div>

      <button className="button" onClick={handleUpdateLocation}>
        Update Location
      </button>
      <Footer />
    </div>
  );
};

export default UpdateLocationAdmin;
