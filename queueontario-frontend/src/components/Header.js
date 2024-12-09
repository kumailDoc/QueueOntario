import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import '../styles/Header.css';

const Header = () => {
  const navigate = useNavigate();

  // Retrieve user info from localStorage (USE THIS for other components)
  const userInfo = JSON.parse(localStorage.getItem('userInfo'));

  // Logout function
  const handleLogout = () => {
    // Remove user info from localStorage. 
    localStorage.removeItem('userInfo');

    // Redirect to login page
    navigate('/login');
  };

  const handleCheckWaitlist = () => {
    navigate('/checkwaitlist');
  }

  return (
    <header className='header-body'>
      <nav className="nav-left">
        <ul>
          <li><Link to="/">Home</Link></li>
          <li><Link to="/contact">Contact</Link></li>
          <li><Link to="/about">About</Link></li>
        </ul>
      </nav>
      <nav className="nav-right">
        <ul>
          {userInfo ? (
            // Display username and logout button if logged in
            <>
              {userInfo.roles[0] === 'ROLE_ADMIN' && (
                <li><Link to="/admin" className='adminBtn'>Admin Portal</Link></li>
              )}
              {userInfo.roles[0] === 'ROLE_MODERATOR' && (
                <li><Link to='/mod' className="modBtn">Mod Portal</Link></li>
              )}
              <li><button onClick={handleCheckWaitlist} className="cwaitlist-button">Check Waitlist</button></li>
              <li className='greeting'>Welcome, {userInfo.username}</li>
              <li><button onClick={handleLogout} className="logout-button">Log Out</button></li>
            </>
          ) : (
            // Display Log In / Sign Up links if not logged in
            <>
              <li><Link to="/login">Log In</Link></li>
              <li>/</li>
              <li><Link to="/signup">Sign Up</Link></li>
            </>
          )}
        </ul>
      </nav>
    </header>
  );
};

export default Header;