MERGE (c:City {name: "Florence", Geolat: 43.77925, Geolng: 11.24626});
MERGE (c:City {name: "Naples", Geolat: 26.14234, Geolng: -81.79596});
MERGE (c:City {name: "Venice", Geolat: 27.09978, Geolng: -82.45426});
MERGE (c:City {name: "Paris", Geolat: 38.2098, Geolng: -84.25299});
MERGE (c:City {name: "King", Geolat: 36.28069, Geolng: -80.35922});

MATCH (c:City {name: "Florence"})
MERGE (b:Book {name: "The Complete Works of William Shakespeare"})
MERGE (a:Author {name: "Shakespeare, William"})
MERGE (a)-[r:Authored]->(b)
MERGE (b)-[:Mentions]->(c);

MATCH (c:City {name: "Florence"})
MERGE (b:Book {name: "La Fiammetta"})
MERGE (a:Author {name: "Boccaccio, Giovanni"})
MERGE (a)-[r:Authored]->(b)
MERGE (b)-[:Mentions]->(c);

MATCH (c:City {name: "Florence"})
MERGE (b:Book {name: "Divine Comedy, Longfellow's Translation, Hell"})
MERGE (a:Author {name: "Dante Alighieri"})
MERGE (a)-[r:Authored]->(b)
MERGE (b)-[:Mentions]->(c);

MATCH (c:City {name: "Florence"})
MERGE (b:Book {name: "A Beautiful Possibility"})
MERGE (a:Author {name: "Black, Edith Ferguson"})
MERGE (a)-[r:Authored]->(b)
MERGE (b)-[:Mentions]->(c);

MATCH (c:City {name: "Florence"})
MERGE (b:Book {name: "The Mystery of the Boule Cabinet: A Detective Story"})
MERGE (a:Author {name: "Stevenson, Burton Egbert"})
MERGE (a)-[r:Authored]->(b)
MERGE (b)-[:Mentions]->(c);