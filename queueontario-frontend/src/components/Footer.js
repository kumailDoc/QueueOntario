import React from 'react';
import '../styles/Footer.css'

const Footer = () => {
  return (
    <footer className='footer-body'>
      <a href="https://www.ontario.ca/page/serviceontario" className="service-ontario-link"><p>ServiceOntario</p></a>
      <a href="/reportissue" className="report-issue-link"><p>Report Issue</p></a>
    </footer>
  );
};

export default Footer;