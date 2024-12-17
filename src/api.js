import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

export const fetchJavaTopics = async (n) => axios.get(`${API_BASE_URL}/topics`, { params: { n } });
export const fetchUserEngagement = async (n) => axios.get(`${API_BASE_URL}/user-engagement`, { params: { n } });
export const fetchCommonMistakes = async (n) => axios.get(`${API_BASE_URL}/common-mistakes`, { params: { n } });
export const fetchAnswerQuality = async (n) => axios.get(`${API_BASE_URL}/answer-quality`, { params: { n } });
