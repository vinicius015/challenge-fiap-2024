import 'package:flutter/material.dart';

import '../../config/globals.dart';

class DecoratedTextFormField extends StatelessWidget {
  final String label;
  final TextEditingController controller;
  const DecoratedTextFormField(
      {super.key, required this.label, required this.controller});

  @override
  Widget build(BuildContext context) {
    return TextFormField(
      controller: controller,
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
