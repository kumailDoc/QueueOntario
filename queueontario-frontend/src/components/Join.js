import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Header from "./Header";
import Footer from "./Footer";
import "../styles/Join.css";

const JoinWaitList = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const { centerId } = location.state;
    const userInfo = JSON.parse(localStorage.getItem('userInfo'));

    const [formData, setFormData] = useState({
        service: "",
        email: "",
        name: "",
        phone: "",
    });

    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState("");

    //if user is not logged in, show the login prompt
    if (!userInfo) {
        return <div>Please log in to join the waitlist.</div>;
    }

    //handle form input changes
    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        //ensure user is logged in
        if (!userInfo || !userInfo.id) {
            setError("User not logged in. Please log in to join the waitlist.");
            return;
        }

        const userId = userInfo.id;

        try {
            const response = await fetch('http://localhost:8080/api/waitlists/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    serviceOntarioCenterId: centerId,
                    userId,
                    service: formData.service,
                    email: formData.email,
                    name: formData.name,
                    phone: formData.phone,
                }),
            });

            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }

            const data = await response.json();
            console.log('Successfully joined waitlist:', data);

            //Store the waitlist data
            localStorage.setItem('waitlistInfo', JSON.stringify(data));

            setSuccessMessage("Successfully joined the waitlist!");

            setTimeout(() => {
                navigate('/checkwaitlist');
            }, 1500);  

        } catch (error) {
            console.error('Error joining waitlist:', error);
            setError('Failed to join the waitlist. Please try again.');
        }
    };

    return (
        <div className="join-wrapper">
            <Header />
            <div className="join-container">
                <h1>Join Waitlist</h1>
                {error && <p className="error-message">{error}</p>}
                {successMessage && <p className="success-message">{successMessage}</p>}
                <form className="join-form" onSubmit={handleSubmit}>
                    <label htmlFor="service">Service:</label>
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

                    <label htmlFor="email">Email Address:</label>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        value={formData.email}
                        onChange={handleInputChange}
                        required
                    />

                    <label htmlFor="name">Name:</label>
                    <input
                        type="text"
                        id="name"
                        name="name"
                        value={formData.name}
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
                        required
                        pattern="[0-9]{10}"
                    />

                    <button type="submit" className="circular-button-join">
                        Join Waitlist
                    </button>
                </form>
            </div>
            <Footer />
        </div>
    );
};

export default JoinWaitList;
