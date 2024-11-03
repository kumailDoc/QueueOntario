import React, { useState } from 'react';
import Header from './Header';
import Footer from './Footer';
import '../styles/Join.css'; // Import the new CSS file

const JoinWaitList = () => {
  const [formData, setFormData] = useState({
    pincode: '',
    service: '',
    name: '',
    email: '',
    phone: ''
  });

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // Add your submission logic here
  };

  return (
    <div className="join-wrapper">
      <Header />
      <div className="join-container">
        <h1>Join Waitlist</h1>
        <form className="join-form" onSubmit={handleSubmit}>
          <label htmlFor="pincode">Pincode:</label>
          <input
            type="text"
            id="pincode"
            name="pincode"
            value={formData.pincode}
            onChange={handleInputChange}
            required
          />
          
          <label htmlFor="service">Nearest Service Ontario:</label>
          <select
            id="service"
            name="service"
            value={formData.service}
            onChange={handleInputChange}
            required
          >
            <option value="">Select Service</option>
            <option value="HealthCardRenewal">Health Card Renewal</option>
            <option value="DriverLicenceRenewal">Driver Licence Renewal</option>
            <option value="LicensePlate">License Plate</option>
            <option value="ParkingPermit">Parking Permit</option>
            <option value="OntarioPhotoCard">Ontario Photo Card</option>
          </select>
          
          <label htmlFor="name">Name:</label>
          <input
            type="text"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleInputChange}
            required
          />
          
          <label htmlFor="email">Email Address:</label>
          <input
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleInputChange}
            required
          />
          
          <label htmlFor="phone">Phone Number:</label>
          <input
            type="tel"
            id="phone"
            name="phone"
            value={formData.phone}
            onChange={handleInputChange}
            pattern="[0-9]{10}"
            required
          />
          
          <button type="submit" className="circular-button-join">Join Waitlist</button>
        </form>
      </div>
      <Footer />
    </div>
  );
};

export default JoinWaitList;
