import axios from 'axios';

const API_BASE_URL = '/Project-Final';


// CommonMistakeController
export const fetchCommonMistakes = async (n) =>
  axios.get(`${API_BASE_URL}/CommonMistake/CommonMistake`, { params: { n } });

// AnswerQualityController
export const fetchFirstAnswerAcceptedRatio = async () =>
  axios.get(`${API_BASE_URL}/AnswerQuality/firstAnswerAcceptedRatio`);

export const fetchAcceptedAnswerRatio = async () =>
  axios.get(`${API_BASE_URL}/AnswerQuality/acceptedAnswerRatio`);

export const fetchHighQualityAnswerByHighReputationUserRatio = async (voteThreshold, reputationThreshold) =>
  axios.get(`${API_BASE_URL}/AnswerQuality/highQualityAnswerByHighReputationUserRatio`, {
    params: { voteThreshold, reputationThreshold },
  });

export const fetchHighQualityAnswerLengthDistribution = async (voteThreshold) =>
  axios.get(`${API_BASE_URL}/AnswerQuality/highQualityAnswerLengthDistribution`, { params: { voteThreshold } });

// JavaTopicAnalysisController
export const fetchTopJavaTopics = async (num) =>
  axios.get(`${API_BASE_URL}/javaTopicAnalysis/TopTopics/${num}`);

export const fetchSpecificJavaTopics = async (topics) =>
  axios.get(`${API_BASE_URL}/javaTopicAnalysis/SpecificTopics/${topics}`);

// RestfullAPIController
export const fetchBugFrequency = async (error, n) =>
  axios.get(`${API_BASE_URL}/restfullAPI/bugFrequency`, { params: { error, n } });

// UserEngagementController
export const fetchTopUserEngagement = async (reputationLimit, n) =>
  axios.get(`${API_BASE_URL}/UserEngagement/TopUserEngagement`, { params: { reputationLimit, n } });
