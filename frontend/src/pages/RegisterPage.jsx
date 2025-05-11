
import React, { useState } from 'react';
import axios from 'axios';

const RegisterPage = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: ''
  });

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
    } catch (err) {
      console.error(err);
      alert("Błąd rejestracji");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Rejestracja</h2>
      <input type="text" name="username" placeholder="Nazwa użytkownika" onChange={handleChange} />
      <input type="email" name="email" placeholder="Email" onChange={handleChange} />
      <input type="password" name="password" placeholder="Hasło" onChange={handleChange} />
      <button type="submit">Zarejestruj się</button>
    </form>
  );
};

export default RegisterPage;