import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import Header from './Header';
import Footer from './Footer';
import '../styles/ServiceOntarioList.css';

const ServiceOntarioList = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [centers, setCenters] = useState([]);
  const selectedLocation = location.state?.location;

  useEffect(() => {
    if (selectedLocation) {
      fetch(`http://localhost:8080/api/serviceontario/centers?city=${selectedLocation}`)
        .then(response => response.json())
        .then(data => setCenters(data.slice(0, 3)))  // Limit to max 3 centers
        .catch(error => console.error('Error fetching ServiceOntario centers:', error));
    }
  }, [selectedLocation]);

  const handleJoinWaitlist = (center) => {
    navigate('/join', { state: { center } });
  };

  return (
    <div className="service-ontario-list">
      <Header />
      <div className="centers-container">
        <h2 className="page-title">ServiceOntario Centers in {selectedLocation}</h2>
        {centers.length > 0 ? (
          <div className="center-cards">
            {centers.map((center) => (
              <div className="center-card" key={center.id}>
                <h3>{center.name}</h3>
                <p>{center.address}</p>
                <button
                  className="join-button"
                  onClick={() => handleJoinWaitlist(center)}
                >
                  Join Waitlist
                </button>
              </div>
            ))}
          </div>
        ) : (
          <p className="no-centers">No ServiceOntario centers found for this location.</p>
        )}
      </div>
      <Footer />
    </div>
  );
};

export default ServiceOntarioList;
