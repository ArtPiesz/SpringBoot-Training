import React from 'react';
import { Link } from 'react-router-dom';
import { Container, Button } from 'react-bootstrap';

const HomePage = () => {
  return (
    <Container className="text-center mt-5">
      <h1>Witamy w Travel Planner</h1>
      <p>Zarządzaj swoimi podróżami w prosty sposób</p>
      <Link to="/login">
        <Button variant="primary" className="m-2">Zaloguj się</Button>
      </Link>
      <Link to="/register">
        <Button variant="outline-primary" className="m-2">Zarejestruj się</Button>
      </Link>
    </Container>
  );
};

export default HomePage;