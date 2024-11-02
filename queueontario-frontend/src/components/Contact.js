import React from 'react';
import Header from './Header';
import Footer from './Footer';
import '../styles/About.css';

const About = () => {
  return (
    <div className="about-page">
      <Header />
      <div className="contact-container">
        <h1>Conatct QueueOntario</h1>
        <p>This will be the contact page.</p>
      </div>
      <Footer />
    </div>
  );
};

export default About;
