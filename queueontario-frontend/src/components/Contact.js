import React from 'react';
import Header from './Header';
import Footer from './Footer';
import '../styles/About.css';

const Contact = () => {
  return (
    <div style={{ padding: "20px" }}>
    <Header />
      <h1>Contact Us</h1>
      <p>Feel free to reach out to us through the following methods:</p>
      <ul>
        <li><strong>Phone:</strong> +1 (555) 123-4567</li>
        <li><strong>Email:</strong> contact@example.com</li>
        <li><strong>Address:</strong> 123 Main St, Toronto, Ontario, Canada</li>
      </ul>
      <Footer />
    </div>
  );
};

export default Contact;
