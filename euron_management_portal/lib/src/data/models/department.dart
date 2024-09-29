import 'dart:convert';

class Department {
  int id;
  String department;
  String description;

  Department({
    required this.id,
    required this.department,
    required this.description,
  });

  factory Department.fromRawJson(String str) => Department.fromJson(json.decode(str));

  factory Department.fromJson(Map<String, dynamic> json) => Department(
    id: json["id"]!,
    department: json["department"]!,
    description: json["description"]!,
  );

    String toRawJson() => json.encode(toJson());

  Map<String, dynamic> toJson() => {
    "id": id,
    "department": department,
    "description": description,
  };
}
