import React, { useState, useEffect } from 'react';
import '../styles/Issues.css';
import Header from './Header';
import Footer from './Footer';

const AdminIssue = () => {
  const [issues, setIssues] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchIssues = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/report');
        if (!response.ok) {
          throw new Error('Failed to fetch issues');
        }
        const data = await response.json();
        setIssues(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchIssues();
  }, []);

  return (
    <div className="issues-page">
      <Header />
      <div className="issues-container">
        <h1>Submitted Issues</h1>
        {loading && <p>Loading issues...</p>}
        {error && <p className="error-message">{error}</p>}
        {!loading && !error && issues.length === 0 && <p>No issues found.</p>}
        {!loading && !error && issues.map((issue) => (
          <div key={issue._id} className="issue-card">
            <h3>{issue.name}</h3>
            <p><strong>Issue Type:</strong> {issue.issue.replace('_', ' ')}</p>
            <p><strong>Comments:</strong> {issue.comments}</p>
            <p><strong>Email:</strong> {issue.email || 'Not provided'}</p>
            <p><strong>Submitted At:</strong> {new Date(issue.createdAt).toLocaleString()}</p>
          </div>
        ))}
      </div>
      <Footer />
    </div>
  );
};

export default AdminIssue;
