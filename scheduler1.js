
let availabilities = [];
function submitAvailability() {
  const name = document.getElementById('name').value;
  const date = document.getElementById('dates').value;
  const startTime = document.getElementById('startTime').value;
  const endTime = document.getElementById('endTime').value;

  if (!name || !date || !startTime || !endTime) {
    alert('Please fill in all fields.');
    return;
  }

  if (convertToMinutes(endTime) <= convertToMinutes(startTime)) {
    alert('End time must be later than start time. Please adjust your input.');
    return;
  }

  availabilities.push({ name, date, startTime, endTime });

  updateChart();
  calculateBestTime();
}

document.getElementById('availabilityForm').addEventListener('submit', function (event) {
  event.preventDefault(); 
  submitAvailability();
});

function updateChart() {
  const tableBody = document.getElementById('availabilityTable').querySelector('tbody');
  tableBody.innerHTML = '';

  availabilities.forEach(({ name, date, startTime, endTime }) => {
    const row = document.createElement('tr');
    row.innerHTML = `<td>${name}</td><td>${date}</td><td>${startTime} - ${endTime}</td>`;
    tableBody.appendChild(row);
  });
}

function calculateBestTime() {
  if (availabilities.length === 0) {
    document.getElementById('bestTime').textContent = 'No data available.';
    return;
  }

  const dateGroups = availabilities.reduce((groups, { date, startTime, endTime }) => {
    if (!groups[date]) groups[date] = [];
    groups[date].push({ startTime, endTime });
    return groups;
  }, {});

  const dateParticipation = Object.entries(dateGroups).map(([date, times]) => ({
    date,
    count: times.length,
  }));

  const bestDate = dateParticipation.sort((a, b) => b.count - a.count)[0]?.date;

  if (!bestDate) {
    document.getElementById('bestTime').textContent = 'No common date found.';
    return;
  }

  const times = dateGroups[bestDate];
  let latestStart = Math.max(...times.map(t => convertToMinutes(t.startTime)));
  let earliestEnd = Math.min(...times.map(t => convertToMinutes(t.endTime)));

  let bestTimeMessage = `Best time is on ${bestDate} `;
  if (latestStart < earliestEnd) {
    bestTimeMessage += `from ${convertToTime(latestStart)} to ${convertToTime(earliestEnd)}.`;
  } else {
    bestTimeMessage += 'but no common time slot found.';
  }

  document.getElementById('bestTime').textContent = bestTimeMessage;
}

function convertToMinutes(time) {
  const [hours, minutes] = time.split(':').map(Number);
  return hours * 60 + minutes;
}

function convertToTime(minutes) {
  const hours = Math.floor(minutes / 60).toString().padStart(2, '0');
  const mins = (minutes % 60).toString().padStart(2, '0');
  return `${hours}:${mins}`;
}
