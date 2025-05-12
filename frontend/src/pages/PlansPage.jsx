import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Container, Button, Card, Row, Col } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const PlansPage = () => {
  const [plans, setPlans] = useState([]);

  useEffect(() => {
    const fetchPlans = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.get('http://localhost:8080/travel-plans', {
          headers: { Authorization: `Bearer ${token}` }
        });
        setPlans(response.data);
      } catch (err) {
        console.error('Błąd:', err);
        alert('Błąd podczas pobierania planów');
      }
    };
    fetchPlans();
  }, []);

    const handleDelete = async (planId) => {
      if (!window.confirm('Czy na pewno chcesz usunąć ten plan?')) return;

      try {
        const token = localStorage.getItem('token');
        await axios.delete(`http://localhost:8080/travel-plans/${planId}`, {
          headers: { Authorization: `Bearer ${token}` }
        });
        setPlans(prevPlans => prevPlans.filter(plan => plan.id !== planId));
        alert('Plan został usunięty');
      } catch (err) {
        console.error('Błąd podczas usuwania planu:', err);
        alert('Nie udało się usunąć planu');
      }
    };

  return (
     <Container>
       <h2>Plany podróży</h2>
       <Button variant="primary" as={Link} to="/plans/add">Dodaj nowy plan</Button>
       <Row className="mt-4">
         {plans.map(plan => (
           <Col md={4} key={plan.id} className="mb-4">
             <Card>
               <Card.Body>
                 <Card.Title>{plan.title}</Card.Title>
                 <Card.Subtitle className="mb-2 text-muted">{plan.destination}</Card.Subtitle>
                 <Card.Text>{plan.description}</Card.Text>
                 <Card.Text>
                   <small>Start: {plan.startDate} - End: {plan.endDate}</small>
                 </Card.Text>
                 <div className="d-flex justify-content-between">
                   <Button
                     variant="warning"
                     as={Link}
                     to={`/plans/edit/${plan.id}`}
                   >
                     Edytuj
                   </Button>
                   <Button
                     variant="danger"
                     onClick={() => handleDelete(plan.id)}
                   >
                     Usuń
                   </Button>
                 </div>
               </Card.Body>
             </Card>
           </Col>
         ))}
       </Row>
     </Container>
   );
 };

export default PlansPage;