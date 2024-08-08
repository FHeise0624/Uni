/*
FileName: bar_chart_make.js
Author: Vanessa Pires
Description: Dieses Skript lädt Daten aus einer Beispiel-JSON-Datei (rede.json) und erstellt ein Balkendiagramm, das die Häufigkeit von bestimmten Tags (POS-Tags) visualisiert. 
Es nutzt D3.js, um die Anzahl der Vorkommen jedes Tags zu berechnen, eine Skalierung für die X- und Y-Achsen zu erstellen und die Balken entsprechend dieser Häufigkeit zu positionieren und zu dimensionieren.
Interaktive Elemente wie Tooltips werden hinzugefügt, um zusätzliche Informationen beim Überfahren der Balken mit der Maus anzuzeigen.
*/

// Lädt Daten asynchron aus der Datei "rede.json"
d3.json("rede.json").then(function(data) {
    // Extrahiert POS-Tags aus den geladenen Daten
    var posTags = data.pos; 

    // Zählt, wie oft jeder Tag vorkommt
    var tagCounts = {};
    posTags.forEach(function(tag) {
        tagCounts[tag] = (tagCounts[tag] || 0) + 1;
    });

    // Wandelt das Objekt `tagCounts` in ein Array von [key, value]-Paaren um
    var data = Object.entries(tagCounts);

    // Konfiguriert das SVG-Element für das Balkendiagramm
    var svg = d3.select('svg'),
        margin = { top: 50, right: 50, bottom: 50, left: 50 },
        width = svg.attr('width') - margin.left - margin.right,
        height = svg.attr('height') - margin.top - margin.bottom;

    // Definiert die Skalen für die X- und Y-Achsen
    var xScale = d3.scaleBand().range([0, width]).padding(0.4),
        yScale = d3.scaleLinear().range([height, 0]);

    // Fügt eine Gruppe (`<g>`) im SVG hinzu, die als Container für das Diagramm dient
    var g = svg.append('g')
        .attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');

    // Setzt die Domänen für die Skalen basierend auf den Daten
    xScale.domain(data.map(function(d) { return d[0]; }));
    yScale.domain([0, d3.max(data, function(d) { return d[1]; })]);

    // Fügt die X-Achse unten am Diagramm hinzu
    g.append('g')
        .attr('transform', 'translate(0,' + height + ')')
        .call(d3.axisBottom(xScale))
        .selectAll("text")
        .style("text-anchor", "end")
        .attr("dx", "-.8em")
        .attr("dy", ".15em")
        .attr("transform", "rotate(-45)");

    // Fügt die Y-Achse links am Diagramm hinzu
    g.append('g')
        .call(d3.axisLeft(yScale));

    // Definiert eine Farbskala für die Balken
    var colorScale = d3.scaleOrdinal(d3.schemeCategory10);

    // Fügt die Balken zum Diagramm hinzu
    g.selectAll('.bar')
        .data(data)
        .enter().append('rect')
        .attr('class', 'bar')
        .attr('x', function(d) { return xScale(d[0]); })
        .attr('y', function(d) { return yScale(d[1]); })
        .attr('width', xScale.bandwidth())
        .attr('height', function(d) { return height - yScale(d[1]); })
        .attr('fill', function(d, i) { return colorScale(i); }) // Weist jeder Balken eine Farbe zu
        .on('mouseover', function(d) {
            // Zeigt einen Tooltip bei Mouseover über einen Balken
            tooltip.style("opacity", 1);
            tooltip.html("Häufigkeit: " + d[1])
                .style("left", (d3.event.pageX) + "px")
                .style("top", (d3.event.pageY - 28) + "px");
        })
        .on('mouseout', function() {
            // Entfernt den Tooltip, wenn die Maus den Balken verlässt
            tooltip.style("opacity", 0);
        });
});
