class Department {
  final String _name;
  final String _description;

  Department({
    required String name,
    required String description
  })
  : _name = name,
  _description = description;

  String get name => _name;
  String get description => _description;
}