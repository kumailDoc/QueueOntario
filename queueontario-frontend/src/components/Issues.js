import React, { useState, useEffect } from 'react';
import '../styles/Issues.css';
import Header from './Header';
import Footer from './Footer';

const IssuesPage = () => {
    const [issues, setIssues] = useState([]);
    const [error, setError] = useState(null);

    const userInfo = JSON.parse(localStorage.getItem('userInfo')); // Parse userInfo from localStorage

    useEffect(() => {
        if (!userInfo || !userInfo.roles.some(role => role === 'ROLE_ADMIN' || role === 'ROLE_MODERATOR')) {
            setError('You do not have permission to view these issues.');
            return;
        }

        const fetchIssues = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/report', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                if (response.ok) {
                    const data = await response.json();

                    // Sort issues by createdAt (oldest to newest)
                    const sortedIssues = data.sort((a, b) => new Date(a.createdAt) - new Date(b.createdAt));
                    setIssues(sortedIssues);
                } else if (response.status === 403) {
                    setError('Forbidden: You do not have permission to view these issues.');
                } else {
                    setError('Failed to fetch issues.');
                }
            } catch (err) {
                setError('An error occurred. Please try again.');
                console.error('Error fetching issues:', err);
            }
        };

        fetchIssues();
    }, []);

    const handleDelete = async (id) => {
        try {
            const response = await fetch(`http://localhost:8080/api/report/${id}`, {
                method: 'DELETE',
            });

            if (response.ok) {
                alert('Issue deleted successfully!');
                setIssues(issues.filter(issue => issue.id !== id)); // Update the UI
            } else {
                alert('Failed to delete the issue.');
            }
        } catch (err) {
            console.error('Error deleting issue:', err);
            alert('An error occurred while deleting the issue.');
        }
    };

    const handleRespond = (email) => {
        const subject = "Response to your reported issue";
        const body = "Hello,\n\nWe are reaching out regarding your submitted issue.\n\nThank you.";
        window.location.href = `mailto:${email}?subject=${encodeURIComponent(subject)}&body=${encodeURIComponent(body)}`;
    };

    return (
        <div className="issues-container">
            <Header />
            <h1>Reported Issues</h1>
            {error && <p className="error-message">{error}</p>}
            {!error && issues.length === 0 && <p className="no-issues">No issues to display.</p>}
            <ul className="issues-list">
            <ul className="issues-list">

            {issues.map(issue => (
                <li key={issue.id} className="issue-card">
                    <div className="issue-title">{issue.name}</div>
                    <div className="issue-content">{issue.comments}</div>
                    <div className="issue-email">Email: {issue.email}</div>
                    <div className="issue-createdAt">
                        Posted: {new Date(issue.createdAt).toLocaleString()}
                    </div>
                    <div className="issue-actions">
                        <button
                            className="delete-button"
                            onClick={() => handleDelete(issue.id)}
                        >
                            Delete
                        </button>
                        <button
                            className="respond-button"
                            onClick={() => handleRespond(issue.email)}
                        >
                            Respond
                        </button>
                    </div>
                </li>
            ))}
        </ul>
            </ul>
            <Footer />
        </div>
    );
};

export default IssuesPage;