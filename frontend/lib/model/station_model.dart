class Station{
  String id;
  String userId;
  int maxOutputPower;
  double energy;
  int gridConnection;
  int stationConnection;

  Station({this.id, this.userId, this.maxOutputPower, this.energy, this.gridConnection, this.stationConnection});

  Map<String, dynamic> toMap(){
    return {
      'id' : id,
      'userId' : userId,
      'maxOutputPower' : maxOutputPower,
      'energy' : energy,
      'gridConnection' : gridConnection,
      'stationConnection' : stationConnection,
    };
  }

  factory Station.fromMap(Map<String, dynamic> map){
    return Station(
      id: map['id'] as String,
      userId: map['userId'] as String,
      maxOutputPower: map['maxOutputPower'] as int,
      energy: map['energy'] as double,
      gridConnection: map['gridConnection'] as int,
      stationConnection: map['stationConnection'] as int,
    );
  }
}