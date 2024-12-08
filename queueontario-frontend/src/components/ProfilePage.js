import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../styles/ProfilePage.css';

const ProfilePage = () => {
  const navigate = useNavigate();

  // State variables for profile details
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');

  // Retrieve the auth token and user info
  const userInfo = JSON.parse(localStorage.getItem('userInfo'));
  const token = userInfo?.token;

  // Fetch user details on component mount
  useEffect(() => {
    const user = localStorage.getItem('userInfo')
    if (!user) {
      navigate('/login'); // Redirect to login if not authenticated
    } 
    const userInfo = JSON.parse(user)
    const token = userInfo?.token
    
      axios
      .get('/api/auth/profile', {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        const { username, email } = response.data;
        setUsername(username);
        setEmail(email);
      })
      .catch((error) => {
        console.error('Error fetching user profile:', error);
        setMessage('Failed to load profile. Please try again.');
      });
    }, 
    [token, navigate]);

  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage('');

    const updatedData = {};
    if (username) updatedData.username = username;
    if (email) updatedData.email = email;
    if (password) updatedData.password = password;

    axios
      .patch('/api/auth/edit-profile', updatedData, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then(() => {
        setMessage('Profile updated successfully!');
      })
      .catch((error) => {
        console.error('Error updating profile:', error);
        setMessage('Failed to update profile. Please try again.');
      })
      .finally(() => setLoading(false));
  };

  return (
    <div className="profile-page">
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
    </div>
  );
};

export default ProfilePage;
