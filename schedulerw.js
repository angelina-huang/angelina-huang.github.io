let availabilities = [];
function submitAvailability() {
  const name = document.getElementById('name').value;
  const day = document.getElementById('days').value;
  const startTime = document.getElementById('startTime').value;
  const endTime = document.getElementById('endTime').value;

  if (!name || !day || !startTime || !endTime) {
    alert('Please fill in all fields.');
    return;
  }

  if (convertToMinutes(endTime) <= convertToMinutes(startTime)) {
    alert('End time must be later than start time. Please adjust your input.');
    return;
  }

  availabilities.push({ name, day, startTime, endTime });
  updateChart();
  calculateBestTime();
}

document.getElementById('weekly-meeting-form').addEventListener('submit', function (event) {
  event.preventDefault(); 
  submitAvailability();
});

function updateChart() {
  const tableBody = document.getElementById('availabilityTable').querySelector('tbody');
  tableBody.innerHTML = '';
  availabilities.forEach(({ name, day, startTime, endTime }) => {
    const row = document.createElement('tr');
    row.innerHTML = `<td>${name}</td><td>${day}</td><td>${startTime} - ${endTime}</td>`;
    tableBody.appendChild(row);
  });
}

function calculateBestTime() {
  if (availabilities.length === 0) {
    document.getElementById('bestTime').textContent = 'No data available.';
    return;
  }

  const dayGroups = availabilities.reduce((groups, { day, startTime, endTime }) => {
    if (!groups[day]) groups[day] = [];
    groups[day].push({ startTime, endTime });
    return groups;
  }, {});

  const dayParticipation = Object.entries(dayGroups).map(([day, times]) => ({
    day,
    count: times.length,
  }));

  const bestDay = dayParticipation.sort((a, b) => b.count - a.count)[0]?.day;

  if (!bestDay) {
    document.getElementById('bestTime').textContent = 'No common day found.';
    return;
  }

  const times = dayGroups[bestDay];
  let latestStart = Math.max(...times.map(t => convertToMinutes(t.startTime)));
  let earliestEnd = Math.min(...times.map(t => convertToMinutes(t.endTime)));

  let bestTimeMessage = `Best time is on ${bestDay} `;
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
