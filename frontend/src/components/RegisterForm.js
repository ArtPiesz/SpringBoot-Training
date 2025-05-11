import React, { useState } from 'react';

const RegisterForm = ({ onRegister }) => {
  const [formData, setFormData] = useState({ username: '', email: '', password: '' });

  const handleChange = (e) => {
    setFormData({...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onRegister(formData);
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Rejestracja</h2>
      <input name="username" placeholder="Login" value={formData.username} onChange={handleChange} required />
      <input name="email" type="email" placeholder="Email" value={formData.email} onChange={handleChange} required />
      <input name="password" type="password" placeholder="Hasło" value={formData.password} onChange={handleChange} required />
      <button type="submit">Zarejestruj</button>
    </form>
  );
};

export default RegisterForm;