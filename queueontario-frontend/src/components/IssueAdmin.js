import React from 'react';
import '../styles/Issues.css';
import Header from './Header';
import Footer from './Footer';

const IssueAdmin = () => {
  //dummy data for now
  const issues = Array(50).fill({
    _id: 'dummy-id',
    name: 'Test User',
    issue: 'location_issue',
    comments: 'This is a test comment.',
    email: 'test.user@example.com',
    createdAt: new Date().toISOString(),
  }).map((issue, index) => ({ ...issue, _id: `${issue._id}-${index}` }));
  

  return (
    <div className="issues-page">
      <Header />
      <div className="issues-container">
        <h1>Submitted Issues</h1>
        {issues.map((issue) => (
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

export default IssueAdmin;
