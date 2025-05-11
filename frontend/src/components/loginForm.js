import React, { useState } from 'react';

const LoginForm = ({ onLogin }) => {
  const [formData, setFormData] = useState({ username: '', password: '' });

  const handleChange = (e) => {
    setFormData({...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onLogin(formData);
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Logowanie</h2>
      <input name="username" placeholder="Login" value={formData.username} onChange={handleChange} required />
      <input name="password" type="password" placeholder="Hasło" value={formData.password} onChange={handleChange} required />
      <button type="submit">Zaloguj</button>
    </form>
  );
};

export default LoginForm;