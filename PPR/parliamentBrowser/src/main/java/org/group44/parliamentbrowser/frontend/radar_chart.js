/*
FileName: radar_chart.js
Author: Vanessa Pires
Description: Dieses Skript erstellt ein Radar-Diagramm, das Stimmungswerte (Sentiments) in drei Kategorien (Neutral, Positiv, Negativ) visualisiert. 
Es verwendet D3.js, um die Stimmungsdaten über drei Kategorien zu verarbeiten.
Das Diagramm umfasst skalierbare Achsen und ist interaktiv gestaltet, so dass Benutzer zusätzliche Informationen erhalten können, indem sie über die verschiedenen Segmente des Diagramms fahren. 
*/

// Beispieldaten für das Radar-Diagramm
const data = [
    { category: "A", value: 0.8 },
    { category: "B", value: 0.6 },
    { category: "C", value: 0.7 },
    { category: "D", value: 0.5 },
    { category: "E", value: 0.9 }
];

// Einrichtung des Radar-Diagramms
const width = 500;  // Breite des SVG-Containers
const height = 500; // Höhe des SVG-Containers
const radius = Math.min(width, height) / 2; // Radius des Diagramms, basierend auf dem kleineren Wert von Höhe oder Breite
const margin = 50; // Rand um das Diagramm herum

// SVG-Element für das Radar-Diagramm erstellen und positionieren
const svg = d3.select('.radar-chart')
    .append('svg')
    .attr('width', width)
    .attr('height', height)
    .append('g')
    .attr('transform', `translate(${width / 2}, ${height / 2})`);

// X-Skala, welche die Kategorien auf den Umfang des Kreises verteilt
const x = d3.scaleBand()
    .range([0, 2 * Math.PI]) // Der Umfang des Kreises, in Radiant
    .domain(data.map(d => d.category)) // Die Kategorien als Domain
    .padding(0.1); // Abstand zwischen den Elementen

// Y-Skala, definiert den Radius basierend auf dem Wert der Daten
const y = d3.scaleLinear()
    .range([0, radius]) // Der Radius, bis wohin die Daten reichen können
    .domain([0, 1]); // Die Werte liegen zwischen 0 und 1

// Verbindende Linien zwischen den Datenpunkten zeichnen
svg.selectAll()
    .data(data)
    .enter()
    .append('line')
    .attr('x1', (d, i) => x(d.category) * Math.cos(Math.PI / 2 - i * 2 * Math.PI / data.length))
    .attr('y1', (d, i) => y(d.value) * Math.sin(Math.PI / 2 - i * 2 * Math.PI / data.length))
    .attr('x2', (d, i) => x(data[(i + 1) % data.length].category) * Math.cos(Math.PI / 2 - (i + 1) * 2 * Math.PI / data.length))
    .attr('y2', (d, i) => y(data[(i + 1) % data.length].value) * Math.sin(Math.PI / 2 - (i + 1) * 2 * Math.PI / data.length))
    .attr('stroke', 'black'); // Farbe der Linien

// Datenpunkte als Kreise hinzufügen
svg.selectAll()
    .data(data)
    .enter()
    .append('circle')
    .attr('cx', (d, i) => x(d.category) * Math.cos(Math.PI / 2 - i * 2 * Math.PI / data.length))
    .attr('cy', (d, i) => y(d.value) * Math.sin(Math.PI / 2 - i * 2 * Math.PI / data.length))
    .attr('r', 4) // Radius der Kreise
    .attr('fill', 'black'); // Farbe der Kreise

// Beschriftungen für die Datenpunkte hinzufügen
svg.selectAll()
    .data(data)
    .enter()
    .append('text')
    .text(d => d.category) // Der Text entspricht der Kategorie
    .attr('x', (d, i) => x(d.category) * Math.cos(Math.PI / 2 - i * 2 * Math.PI / data.length))
    .attr('y', (d, i) => y(d.value) * Math.sin(Math.PI / 2 - i * 2 * Math.PI / data.length))
    .attr('text-anchor', 'middle') // Zentriert den Text auf dem Datenpunkt
    .attr('alignment-baseline', 'middle'); // Stellt sicher, dass der Text vertikal zentriert ist
