import 'package:euron_management_portal/src/config/globals.dart';
import 'package:euron_management_portal/src/presentation/components/custom_appbar.dart';
import 'package:euron_management_portal/src/presentation/components/manual_horizontal_item.dart';
import 'package:flutter/material.dart';

class ComplianceSearchPage extends StatefulWidget {
  const ComplianceSearchPage({super.key});

  @override
  State<ComplianceSearchPage> createState() => _ComplianceSearchPageState();
}

class _ComplianceSearchPageState extends State<ComplianceSearchPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(imagePath: 'euron_logo.png'),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          children: [
            const Row(
              children: [
                Text(
                  'Manuais de Compliance',
                  style: TextStyle(fontSize: 25),
                ),
                SizedBox(width: 15),
                Icon(
                  Icons.filter_list_outlined,
                  color: euronSoftPurple,
                )
              ],
            ),
            ListView.builder(
                scrollDirection: Axis.vertical,
                shrinkWrap: true,
                itemCount: 10,
                itemBuilder: (context, index) {
                  return ManualHorizontalItem();
                })
          ],
        ),
      ),
    );
  }
}
