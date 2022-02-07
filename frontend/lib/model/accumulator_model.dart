class Accumulator{
  String id;
  int maxPower;
  double energy;
  int gridConnection;
  int stationConnection;

  Accumulator({this.id, this.maxPower, this.energy, this.gridConnection, this.stationConnection});

  Map<String, dynamic> toMap(){
    return {
      'id' : id,
      'maxPower' : maxPower,
      'energy' : energy,
      'gridConnection' : gridConnection,
      'stationConnection' : stationConnection,
    };
  }

  factory Accumulator.fromMap(Map<String, dynamic> map){
    return Accumulator(
      id: map['id'] as String,
      maxPower: map['maxPower'] as int,
      energy: map['energy'] as double,
      gridConnection: map['gridConnection'] as int,
      stationConnection: map['stationConnection'] as int,
    );
  }
}