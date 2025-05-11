import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Container, Form, Button, Row, Col } from 'react-bootstrap';


const LoginPage = () => {
  const [formData, setFormData] = useState({
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
      const response = await axios.post('http://localhost:8080/auth/login', formData);
      localStorage.setItem('token', response.data.token);
      alert("Zalogowano pomyślnie");
      navigate('/dashboard');
    } catch (err) {
      console.error(err);
      alert("Nieprawidłowe dane logowania");
    }
  };

  return (
      <Container>
        <h2>Logowanie</h2>
        <Form onSubmit={handleSubmit}>
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

          <Button variant="primary" type="submit">Zaloguj</Button>
        </Form>
      </Container>
    );
  };

export default LoginPage;