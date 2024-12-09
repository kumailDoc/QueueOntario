import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import '../styles/Header.css';

const Header = () => {
  const navigate = useNavigate();
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  // Retrieve user info from localStorage (USE THIS for other components)
  const userInfo = JSON.parse(localStorage.getItem('userInfo'));

  // Logout function
  const handleLogout = () => {
    localStorage.removeItem('userInfo');
    navigate('/login');
  };

  const handleCheckWaitlist = () => {
    navigate('/checkwaitlist');
  };

  // Toggle the hamburger menu
  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  return (
    <header className='header-body'>
      <div className="hamburger-icon" onClick={toggleMenu}>
        <div className="line"></div>
        <div className="line"></div>
        <div className="line"></div>
      </div>

      <nav className={`nav-menu ${isMenuOpen ? 'open' : ''}`}>
        <div className="nav-left">
          <ul>
            <li><Link to="/">Home</Link></li>
            <li><Link to="/contact">Contact</Link></li>
            <li><Link to="/about">About</Link></li>
          </ul>
        </div>

        <div className="nav-right">
          <ul>
            {userInfo ? (
              <>
                {userInfo.roles[0] === 'ROLE_ADMIN' && (
                  <li><Link to="/admin" className='adminBtn'>Admin Portal</Link></li>
                )}
                {userInfo.roles[0] === 'ROLE_MODERATOR' && (
                  <li><Link to='/mod' className="modBtn">Mod Portal</Link></li>
                )}
                <li>
                  <button onClick={handleCheckWaitlist} className="cwaitlist-button">
                    Check Waitlist
                  </button>
                </li>
                <li className='greeting'>Welcome, {userInfo.username}</li>
                <li>
                  <button onClick={handleLogout} className="logout-button">
                    Log Out
                  </button>
                </li>
              </>
            ) : (
              <>
                <li><Link to="/login">Log In</Link></li>
                <li>/</li>
                <li><Link to="/signup">Sign Up</Link></li>
              </>
            )}
          </ul>
        </div>
      </nav>
    </header>
  );
};

export default Header;
