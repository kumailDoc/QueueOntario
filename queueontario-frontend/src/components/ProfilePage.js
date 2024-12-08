import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../styles/ProfilePage.css';
import Header from './Header';
import Footer from "./Footer";

const ProfilePage = () => {
  const navigate = useNavigate();

  // State variables for profile details
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');
  const [userInfo, setUserInfo] = useState(null); // Store user info
  const [token, setToken] = useState(''); // JWT Token

  // **Load user info from localStorage** when the component mounts
  useEffect(() => {
    const storedUserInfo = JSON.parse(localStorage.getItem('userInfo'));
    if (!storedUserInfo) {
      setMessage('No user is logged in. Please log in to view user information.');
      navigate('/login'); // Redirect to login if no user is found
      return;
    }

    setUserInfo(storedUserInfo);
    setToken(storedUserInfo.token);
    setUsername(storedUserInfo.username || ''); // Populate input fields
    setEmail(storedUserInfo.email || '');
  }, [navigate]);

  // **Handle form submission to update user profile**
  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage(''); // Clear any previous messages

    const updatedData = {};
    if (username) updatedData.username = username;
    if (email) updatedData.email = email;
    if (password) updatedData.password = password;

    try {
      // Send the PATCH request to update the profile
      const response = await axios.patch('http://localhost:8080/api/auth/edit-profile', updatedData, {
        headers: { 
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });

      setMessage('Profile updated successfully!'); // Show success message
      setPassword(''); // Clear the password field after successful update

      // Optionally, update localStorage to reflect the changes
      const updatedUserInfo = { ...userInfo, username, email };
      localStorage.setItem('userInfo', JSON.stringify(updatedUserInfo));
      setUserInfo(updatedUserInfo);

    } catch (error) {
      console.error('Error updating profile:', error);

      if (error.response && (error.response.status === 401 || error.response.status === 403)) {
        localStorage.removeItem('userInfo'); // Remove user info from storage
        navigate('/login'); // Redirect to login page
      } else if (error.response && error.response.data && error.response.data.message) {
        setMessage(`Failed to update profile: ${error.response.data.message}`);
      } else {
        setMessage('Failed to update profile. Please try again.');
      }

    } finally {
      setLoading(false);
    }
  };

  if (!userInfo) {
    return <p>{message}</p>;
  }

  return (
    <div className="profile-page">
    <Header/>
      <h2>Edit Profile</h2>
      {message && <p className="message">{message}</p>}
      
      <form onSubmit={handleSubmit} className="profile-form">
        
        <div className="form-group">
          <label htmlFor="username">Username</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            placeholder="Enter new password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>

        <button type="submit" className="submit-button" disabled={loading}>
          {loading ? 'Updating...' : 'Save Changes'}
        </button>
      </form>
      <Footer/>
    </div>
  );
};

export default ProfilePage;
