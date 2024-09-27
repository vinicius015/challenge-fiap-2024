import 'package:flutter/material.dart';

import '../../config/globals.dart';

class DecoratedTextFormField extends StatelessWidget { 
  final String? Function(String?)? validator;
  final String label;
  final TextEditingController controller;
  final bool? isObscure;

  const DecoratedTextFormField(
      {super.key, required this.label, required this.controller, this.validator, this.isObscure, required TextInputType keyboardType});

  @override
  Widget build(BuildContext context) {
    return TextFormField(
      controller: controller,
      validator: validator,
      obscureText: isObscure ?? false,
      decoration: InputDecoration(
          labelText: label,
          labelStyle: const TextStyle(color: euronDarkBlue),
          floatingLabelStyle:
              const TextStyle(color: euronDarkBlue, fontWeight: FontWeight.w700),
          enabledBorder: const OutlineInputBorder(
            borderSide: BorderSide(color: euronDarkBlue),
          ),
          focusedBorder: const OutlineInputBorder(
              borderSide: BorderSide(color: euronCyan, width: 2))),
    );
  }
}
