import 'dart:convert';

import 'package:euron_management_portal/src/data/models/department.dart';

class Position {
  int id;
  String position;
  Department department;

  Position({
    required this.id,
    required this.position,
    required this.department,
  });

  factory Position.fromRawJson(String str) => Position.fromJson(json.decode(str));

  factory Position.fromJson(Map<String, dynamic> json) => Position(
        id: json["id"] ?? 0,
        position: json["position"] ?? '',
        department: Department.fromJson(json["departament"]),
      );

  String toRawJson() => json.encode(toJson());

   Map<String, dynamic> toJson() => {
        "id": id,
        "department": department.toJson(),
      };
}