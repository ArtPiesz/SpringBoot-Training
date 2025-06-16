import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Container, Button, Card, Row, Col } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { Player } from '@lottiefiles/react-lottie-player';

import sunAnimation from '../assets/sun.json';
import rainAnimation from '../assets/rain.json';
import cloudAnimation from '../assets/cloud.json';
import thunderstormAnimation from '../assets/thunderstorm.json';
import snowAnimation from '../assets/snow.json';
import fogAnimation from '../assets/fog.json';

const getAnimationByDescription = (description) => {
  const desc = description.toLowerCase();
  if (desc.includes('thunderstorm')) return thunderstormAnimation;
  if (desc.includes('drizzle') || desc.includes('rain')) return rainAnimation;
  if (desc.includes('snow') || desc.includes('sleet')) return snowAnimation;
  if (desc.includes('mist') || desc.includes('fog') || desc.includes('haze') || desc.includes('smoke') || desc.includes('dust') || desc.includes('ash') || desc.includes('squall') || desc.includes('tornado')) return fogAnimation;
  if (desc.includes('clear')) return sunAnimation;
  if (desc.includes('cloud')) return cloudAnimation;
  return null;
};

const PlansPage = () => {
  const [plans, setPlans] = useState([]);

  const [weatherData, setWeatherData] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    const fetchPlans = async () => {
      try {
        const token = localStorage.getItem('token');
        const plansResp = await axios.get('http://localhost:8080/travel-plans', {
          headers: { Authorization: `Bearer ${token}` }
        });
        setPlans(plansResp.data);


        plansResp.data.forEach(async (plan) => {
          try {
            const weatherResp = await axios.get('http://localhost:8080/weather', {
              params: {
                destination: plan.destination,
                startDate: plan.startDate,
                endDate: plan.endDate,
              },
              headers: { Authorization: `Bearer ${token}` }
            });


            setWeatherData(prev => ({
              ...prev,
              [plan.id]: weatherResp.data
            }));
          } catch (err) {
            console.error(`Błąd pobierania pogody dla planu ${plan.id}`, err);
            setWeatherData(prev => ({
              ...prev,
              [plan.id]: null
            }));
          }
        });
      } catch (err) {
        console.error('Błąd pobierania planów', err);
        alert('Błąd pobierania planów');
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
      setPlans(prev => prev.filter(plan => plan.id !== planId));
      setWeatherData(prev => {
        const copy = { ...prev };
        delete copy[planId];
        return copy;
      });
      alert('Plan został usunięty');
    } catch (err) {
      console.error('Błąd usuwania planu', err);
      alert('Nie udało się usunąć planu');
    }
  };

  return (
    <Container>
      <h2>Plany podróży</h2>
      <Button variant="primary" as={Link} to="/add">Dodaj nowy plan</Button>

      <Row className="mt-4">
        {plans.map(plan => {
          const forecastData = weatherData[plan.id];
          const forecast = forecastData?.forecast || [];
          const averageTemp = forecastData?.averageTemperature ?? null;


          const firstDay = forecast.length > 0 ? forecast[0] : null;
          const animation = firstDay ? getAnimationByDescription(firstDay.description) : null;

          return (
            <Col md={4} key={plan.id} className="mb-4">
              <Card className="position-relative" style={{ overflow: 'hidden', minHeight: '280px' }}>
                {animation && (
                  <Player
                    autoplay
                    loop
                    src={animation}
                    style={{
                      position: 'absolute',
                      top: 0,
                      left: 0,
                      width: '100%',
                      height: '100%',
                      opacity: 0.8,
                      pointerEvents: 'none',
                      zIndex: 0,
                    }}
                  />
                )}
                <Card.Body style={{ position: 'relative', zIndex: 1 }}>
                  <Card.Title>{plan.title}</Card.Title>
                  <Card.Subtitle className="mb-2 text-muted">{plan.destination}</Card.Subtitle>
                  <Card.Text>{plan.description}</Card.Text>
                  <Card.Text><small>Start: {plan.startDate} - Koniec: {plan.endDate}</small></Card.Text>

                  {forecastData ? (
                    <>
                      <Card.Text><strong>Średnia temperatura:</strong> {averageTemp !== null ? averageTemp.toFixed(1) : 'brak danych'} °C</Card.Text>
                      <Card.Text><em>{firstDay?.description}</em></Card.Text>
                    </>
                  ) : (
                    <Card.Text>Ładowanie pogody...</Card.Text>
                  )}
                  <div className="d-flex justify-content-between">
                    <Button variant="warning" as={Link} to={`/plans/edit/${plan.id}`}>
                      Edytuj
                    </Button>
                    <Button variant="danger" onClick={() => handleDelete(plan.id)}>
                      Usuń
                    </Button>
                  </div>
                  <Button variant="link" as={Link} to={`/plans/${plan.id}`}>
                    Szczegóły
                  </Button>
                </Card.Body>
              </Card>
            </Col>
          );
        })}
      </Row>
      <button className="btn btn-secondary mb-3" onClick={() => navigate('/dashboard')}>
        ← Powrót do panelu użytkownika
      </button>
    </Container>
  );
};

export default PlansPage;