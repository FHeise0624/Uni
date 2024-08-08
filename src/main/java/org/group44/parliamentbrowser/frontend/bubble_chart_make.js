/*
FileName: bubble_chart_make.js
Author: Vanessa Pires
Description: Dieses Skript lädt Daten aus einer Beispiel JSON-Datei (rede.json) und erstellt ein Bubble-Chart, das die Häufigkeit von bestimmten Tags (POS-Tags) visualisiert. 
Es nutzt D3.js und berechnet die Anzahl der Vorkommen jedes Tags, erstellt eine Skalierung für die Achsen und die Größe der Bubbles basierend auf dieser Häufigkeit.
Es fügt interaktive Elemente wie Tooltips hinzu, um zusätzliche Informationen beim Überfahren der Bubbles mit der Maus anzuzeigen.
*/

// Laden der Daten aus der JSON-Datei
d3.json("rede.json").then(function(data) {
    var posTags = data.pos; // Extrahieren der POS-Tags aus den Daten

    // Initialisieren eines Objekts zur Zählung der Tags
    var tagCounts = {};
    posTags.forEach(function(tag) {
        tagCounts[tag] = (tagCounts[tag] || 0) + 1; // Zählen der Vorkommen jedes Tags
    });

    // Umwandeln des Zählerobjekts in ein Array für D3
    var bubbleData = Object.entries(tagCounts).map(function(entry) {
        return { tag: entry[0], count: entry[1] };
    });

    // SVG-Canvas vorbereiten
    var svg = d3.select('svg'),
        margin = { top: 50, right: 50, bottom: 50, left: 50 },
        width = svg.attr('width') - margin.left - margin.right,
        height = svg.attr('height') - margin.top - margin.bottom;

    // Skalen für das Bubble-Chart definieren
    var xScale = d3.scaleBand().range([0, width]).padding(0.1),
        yScale = d3.scaleLinear().range([height, 0]),
        radiusScale = d3.scaleSqrt().range([2, 20]); // Anpassen des Bereichs für Bubble-Größen

    // Skalen mit Daten füttern
    xScale.domain(bubbleData.map(function(d) { return d.tag; }));
    yScale.domain([0, d3.max(bubbleData, function(d) { return d.count; })]);
    radiusScale.domain([0, d3.max(bubbleData, function(d) { return d.count; })]);

    // Gruppenelement für das Chart erstellen
    var g = svg.append('g')
        .attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');

    // Bubbles zum Chart hinzufügen
    g.selectAll('.bubble')
        .data(bubbleData)
        .enter().append('circle')
        .attr('class', 'bubble')
        .attr('cx', function(d) { return xScale(d.tag) + xScale.bandwidth() / 2; })
        .attr('cy', function(d) { return yScale(d.count); })
        .attr('r', function(d) { return radiusScale(d.count); })
        .style('fill', 'steelblue')
        .style('opacity', 0.7)
        .on('mouseover', function(d) {
            // Anzeigen des Tooltips beim Mouseover
            tooltip.style("opacity", 1);
            tooltip.html("Tag: " + d.tag + "<br>Häufigkeit: " + d.count)
                .style("left", (d3.event.pageX) + "px")
                .style("top", (d3.event.pageY - 28) + "px");
        })
        .on('mouseout', function() {
            // Entfernen des Tooltips beim Mouseout
            tooltip.style("opacity", 0);
        });

    // Achsen zum Chart hinzufügen
    g.append('g')
        .attr('transform', 'translate(0,' + height + ')')
        .call(d3.axisBottom(xScale))
        .selectAll("text")
        .style("text-anchor", "end")
        .attr("dx", "-.8em")
        .attr("dy", ".15em");

    g.append('g')
        .call(d3.axisLeft(yScale));

    // Tooltip erstellen
    var tooltip = d3.select("body").append("div")
        .attr("class", "tooltip")
        .style("opacity", 0);
});
