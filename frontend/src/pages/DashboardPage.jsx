import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Container, Button } from 'react-bootstrap';

const DashboardPage = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/');
  };

  return (
    <Container className="text-center mt-5">
      <h2>Panel użytkownika</h2>
      <Link to="/plans">
        <Button variant="primary" className="m-2">Moje plany podróży</Button>
      </Link>
      <Button variant="danger" onClick={handleLogout}>Wyloguj się</Button>
    </Container>
  );
};

export default DashboardPage;