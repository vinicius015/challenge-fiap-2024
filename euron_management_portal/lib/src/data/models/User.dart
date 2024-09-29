import 'dart:convert';

import 'package:euron_management_portal/src/data/models/position.dart';

class User {
  int id;
  String re;
  String username;
  String name;
  String email;
  String cpf;
  int number;
  Position position;

  User({
    required this.id,
    required this.re,
    required this.username,
    required this.name,
    required this.email,
    required this.cpf,
    required this.number,
    required this.position,
  });

  factory User.fromRawJson(String str) => User.fromJson(json.decode(str));

  factory User.fromJson(Map<String, dynamic> json) => User(
        id: json["id"]!,
        re: json["re"]!,
        username: json["username"]!,
        name: json["name"]!,
        email: json["email"]!,
        cpf: json["cpf"]!,
        number: json["number"]!,
        position: Position.fromJson(json["position"]),
      );

  String toRawJson() => json.encode(toJson());

  String toRawJsonWithoutId() => json.encode(toJsonWithoutId());

  Map<String, dynamic> toJson() => {
        "id": id,
        "re": re,
        "username": username,
        "name": name,
        "email": email,
        "cpf": cpf,
        "number": number,
        "position": position.id,
      };

        Map<String, dynamic> toJsonWithoutId() => {
        "re": re,
        "username": username,
        "name": name,
        "email": email,
        "cpf": cpf,
        "number": number,
        "position": position.id,
      };
}