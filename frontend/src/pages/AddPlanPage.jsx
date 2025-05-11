import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Container, Form, Button, Row, Col } from 'react-bootstrap';

const AddPlanPage = () => {
   const [formData, setFormData] = useState({
     title: '',
     description: '',
     destination: '',
     startDate: '',
     endDate: ''
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
      const token = localStorage.getItem('token');
      await axios.post('http://localhost:8080/travel-plans', formData, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      alert('Plan dodany!');
      navigate('/plans');
    } catch (err) {
      console.error('Błąd podczas dodawania planu:', err);
      alert('Coś poszło nie tak.');
    }
  };

   return (
      <Container>
        <h2>Dodaj plan podróży</h2>
        <Form onSubmit={handleSubmit}>
          <Form.Group controlId="formTitle">
            <Form.Label>Tytuł planu</Form.Label>
            <Form.Control
              type="text"
              name="title"
              placeholder="Tytuł planu"
              value={formData.title}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group controlId="formDescription">
            <Form.Label>Opis podróży</Form.Label>
            <Form.Control
              as="textarea"
              name="description"
              placeholder="Opis podróży"
              value={formData.description}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group controlId="formDestination">
            <Form.Label>Cel podróży</Form.Label>
            <Form.Control
              type="text"
              name="destination"
              placeholder="Cel podróży"
              value={formData.destination}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group controlId="formStartDate">
            <Form.Label>Data rozpoczęcia</Form.Label>
            <Form.Control
              type="date"
              name="startDate"
              value={formData.startDate}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group controlId="formEndDate">
            <Form.Label>Data zakończenia</Form.Label>
            <Form.Control
              type="date"
              name="endDate"
              value={formData.endDate}
              onChange={handleChange}
            />
          </Form.Group>

          <Button variant="primary" type="submit">Dodaj plan</Button>
        </Form>
      </Container>
    );
  };

export default AddPlanPage;