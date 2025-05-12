import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';
import { Container, Form, Button, Row, Col, Link, Card } from 'react-bootstrap';

const EditPlanPage = () => {
  const { id } = useParams();
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    destination: '',
    startDate: '',
    endDate: ''
  });
  const navigate = useNavigate();

  useEffect(() => {
    const fetchPlanData = async () => {
      const token = localStorage.getItem('token');
      try {
        const response = await axios.get(`http://localhost:8080/travel-plans/${id}`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        setFormData(response.data);
      } catch (err) {
        console.error('Błąd przy pobieraniu danych planu:', err);
        alert('Coś poszło nie tak.');
      }
    };

    fetchPlanData();
  }, [id]);

  const handleChange = (e) => {
    setFormData(prev => ({
      ...prev,
      [e.target.name]: e.target.value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');
    try {
      await axios.put(`http://localhost:8080/travel-plans/${id}`, formData, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      alert('Plan zaktualizowany!');
      navigate('/plans'); // Przejdź do listy planów po zapisaniu
    } catch (err) {
      console.error('Błąd podczas aktualizacji planu:', err);
      alert('Coś poszło nie tak.');
    }
  };

  return (
      <Container className="mt-4">
        <h2 className="text-center mb-4">Edytuj plan podróży</h2>
        <Form onSubmit={handleSubmit}>
          <Row className="mb-3">
            <Col md={6}>
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
            </Col>
            <Col md={6}>
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
            </Col>
          </Row>

          <Form.Group controlId="formDescription" className="mb-3">
            <Form.Label>Opis podróży</Form.Label>
            <Form.Control
              as="textarea"
              rows={3}
              name="description"
              placeholder="Opis podróży"
              value={formData.description}
              onChange={handleChange}
            />
          </Form.Group>

          <Row className="mb-3">
            <Col md={6}>
              <Form.Group controlId="formStartDate">
                <Form.Label>Data rozpoczęcia</Form.Label>
                <Form.Control
                  type="date"
                  name="startDate"
                  value={formData.startDate}
                  onChange={handleChange}
                />
              </Form.Group>
            </Col>
            <Col md={6}>
              <Form.Group controlId="formEndDate">
                <Form.Label>Data zakończenia</Form.Label>
                <Form.Control
                  type="date"
                  name="endDate"
                  value={formData.endDate}
                  onChange={handleChange}
                />
              </Form.Group>
            </Col>
          </Row>

          <Button variant="primary" type="submit" className="w-100">
            Zapisz zmiany
          </Button>
        </Form>
        <button className="btn btn-secondary mb-3" onClick={() => navigate('/plans')} >
          ← Powrót do planów podróży
        </button>
      </Container>
    );
  };

export default EditPlanPage;