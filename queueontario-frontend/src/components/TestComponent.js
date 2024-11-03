import React, { useEffect, useState } from 'react';

const TestComponent = () => {
  const [userInfo, setUserInfo] = useState(null);

  useEffect(() => {
    // Retrieve user info from localStorage
    const storedUserInfo = JSON.parse(localStorage.getItem('userInfo'));
    setUserInfo(storedUserInfo);
  }, []);

  if (!userInfo) {
    return <p>No user is logged in. Please log in to view user information.</p>;
  }

  return (
    <div style={{ padding: '20px', border: '1px solid #ccc', maxWidth: '400px', margin: '20px auto' }}>
      <h2>User Information</h2>
      <p><strong>Id:</strong> {userInfo.id}</p>
      <p><strong>Username:</strong> {userInfo.username}</p>
      <p><strong>Email:</strong> {userInfo.email}</p>
      <p><strong>Roles:</strong> {userInfo.roles.join(', ')}</p>
    </div>
  );
};

export default TestComponent;