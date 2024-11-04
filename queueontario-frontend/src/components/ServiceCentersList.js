import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import Header from './Header';
import Footer from './Footer';
import '../styles/ServiceCentersList.css';

const ServiceCentersList = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { selectedLocation } = location.state;

  const [serviceCenters, setServiceCenters] = useState([]);

  useEffect(() => {
    const fetchServiceCenters = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/serviceontario/centers?city=${selectedLocation}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',  // Remove Authorization header for a public endpoint
          },
        });

        if (!response.ok) {
          throw new Error(`Error: ${response.status}`);
        }

        const data = await response.json();
        setServiceCenters(data);
      } catch (error) {
        console.error('Error fetching service centers:', error);
      }
    };

    if (selectedLocation) {
      fetchServiceCenters();
    }
  }, [selectedLocation]);

  const handleJoin = (centerId) => {
    navigate('/join', { state: { centerId } });
  };

  return (
    <div>
      <Header />
      <div className="service-centers-list">
        <h1>Service Ontario Centers in {selectedLocation}</h1>
        {serviceCenters.length > 0 ? (
          <ul>
            {serviceCenters.map((center) => (
              <li key={center.id}>
                <h2>{center.name}</h2>
                <p>{center.address}</p>
                <button onClick={() => handleJoin(center.id)}>Join Waitlist at {center.name}</button>
              </li>
            ))}
          </ul>
        ) : (
          <p>Loading service centers...</p>
        )}
      </div>
      <Footer />
    </div>
  );
};

export default ServiceCentersList;
