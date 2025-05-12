
import React, { useState } from 'react';
import axios from 'axios';
import { Container, Form, Button, Row, Col } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
const RegisterPage = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: ''
  });
const navigate = useNavigate();
  const handleChange = (e) => {
    setFormData(prev => ({
      ...prev,
      [e.target.name]: e.target.value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/auth/register', formData);
      alert("Rejestracja zakończona sukcesem. Możesz się zalogować.");
      navigate('/login');
    } catch (err) {
      console.error(err);
      alert("Błąd rejestracji");
    }
  };

  return (
      <Container>
        <h2>Rejestracja</h2>
        <Form onSubmit={handleSubmit}>
        <Form.Group controlId="formUsername">
                    <Form.Label>Username</Form.Label>
                    <Form.Control
                      type="username"
                      name="username"
                      placeholder="Wprowadź username"
                      value={formData.username}
                      onChange={handleChange}
                    />
        </Form.Group>
          <Form.Group controlId="formEmail">
            <Form.Label>Email</Form.Label>
            <Form.Control
              type="email"
              name="email"
              placeholder="Wprowadź email"
              value={formData.email}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group controlId="formPassword">
            <Form.Label>Hasło</Form.Label>
            <Form.Control
              type="password"
              name="password"
              placeholder="Wprowadź hasło"
              value={formData.password}
              onChange={handleChange}
            />
          </Form.Group>

          <Button variant="primary" type="submit">Zarejestruj</Button>
        </Form>
      </Container>
    );
  };


export default RegisterPage;