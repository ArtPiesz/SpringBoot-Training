import React, { useState } from 'react';
import axios from 'axios';

const LoginPage = () => {
  const [formData, setFormData] = useState({
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
      const response = await axios.post('http://localhost:8080/auth/login', formData);
      localStorage.setItem('token', response.data.token);
      alert("Zalogowano pomyślnie");
    } catch (err) {
      console.error(err);
      alert("Nieprawidłowe dane logowania");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Logowanie</h2>
      <input
        type="email"
        name="email"
        placeholder="Email"
        value={formData.email}
        onChange={handleChange}
      />
      <input
        type="password"
        name="password"
        placeholder="Hasło"
        value={formData.password}
        onChange={handleChange}
      />
      <button type="submit">Zaloguj się</button>
    </form>
  );
};

export default LoginPage;