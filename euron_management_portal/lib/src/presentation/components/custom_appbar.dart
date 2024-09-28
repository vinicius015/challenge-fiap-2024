import 'package:euron_management_portal/src/config/globals.dart';
import 'package:euron_management_portal/src/presentation/components/bottom_nav_bar.dart';
import 'package:flutter/material.dart';

class CustomAppBar extends StatelessWidget implements PreferredSizeWidget {
  final bool hasHomeButton;
  const CustomAppBar({super.key, required this.hasHomeButton});

  @override
  Widget build(BuildContext context) {
    return AppBar(
      title: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          hasHomeButton
              ? IconButton(
                  color: euronWhite,
                  icon: const Icon(Icons.home_rounded),
                  onPressed: () {
                    Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                        builder: (context) =>
                            const BottomNavBar(isAdmin: true, initialPage: 0),
                      ),
                    );
                  },
                )
              : const SizedBox(width: 48),
          Expanded(
            child: Center(
              child: Image.asset(
                'assets/euron_logo.png',
                fit: BoxFit.cover,
                height: 40,
              ),
            ),
          ),
          const SizedBox(width: 48),
        ],
      ),
      leading: Navigator.canPop(context)
          ? IconButton(
              icon: const Icon(Icons.arrow_back),
              color: euronWhite,
              onPressed: () {
                Navigator.pop(context);
              },
            )
          : null, 
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(kToolbarHeight);
}
