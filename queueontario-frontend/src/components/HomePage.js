import React from 'react';
import { Link } from 'react-router-dom';
import logo from '../assets/logo.JPG';
import '../styles/HomePage.css';
import Header from './Header';
import Footer from './Footer';

const HomePage = () => {
  return (
    <div>
      <Header />
      <div className="main-content">
        <img src={logo} alt="Logo" />
        <div className="location-select">
          <label htmlFor="locations">Available Locations:</label>
          <select id="locations">
            <option value="">-- Select Location --</option>
            <option value="pickering">Pickering</option>
            <option value="markham">Markham</option>
            <option value="scarborough">Scarborough</option>
            <option value="northyork">North York</option>
            <option value="mississauga">Mississauga</option>
            <option value="brampton">Brampton</option>
            <option value="milton">Milton</option>
            <option value="richmondhill">Richmond Hill</option>
            <option value="ajax">Ajax</option>
          </select>
          <Link to="/join" className="circular-button">Join Waitlist Customized for COMP367</Link> 
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default HomePage;
