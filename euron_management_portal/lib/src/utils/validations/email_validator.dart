class EmailValidator {
  static String? validate(String? value) {
    if (value == null || value.isEmpty) {
      return 'O campo de e-mail é obrigatório.';
    }
    final emailRegExp = RegExp(r'^[^@]+@[^@]+\.[^@]+');
    if (!emailRegExp.hasMatch(value)) {
      return 'Por favor, insira um e-mail válido.';
    }
    return '';
  }
}
